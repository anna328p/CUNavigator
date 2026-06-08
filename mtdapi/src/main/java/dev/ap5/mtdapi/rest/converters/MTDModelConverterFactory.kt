package dev.ap5.mtdapi.rest.converters

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import dev.ap5.mtdapi.rest.misc.MApiResult
import dev.ap5.mtdapi.rest.misc.MResult
import dev.ap5.mtdapi.rest.models.APIUsageDay
import dev.ap5.mtdapi.rest.models.CalendarDate
import dev.ap5.mtdapi.rest.models.Departure
import dev.ap5.mtdapi.rest.models.Itinerary
import dev.ap5.mtdapi.rest.models.MTDModel
import dev.ap5.mtdapi.rest.models.Route
import dev.ap5.mtdapi.rest.models.ShapePoint
import dev.ap5.mtdapi.rest.models.Stop
import dev.ap5.mtdapi.rest.models.StopTime
import dev.ap5.mtdapi.rest.models.Trip
import dev.ap5.mtdapi.rest.models.Vehicle
import dev.ap5.mtdapi.rest.responses.MTDResponseBody
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

typealias ConverterTo<T> = Converter.SuspendResponseConverter<HttpResponse, T>

class MTDModelConverterFactory : Converter.Factory {

    private inline fun <reified T : MTDModel> mkConverter(
        crossinline getFromBody : (MTDResponseBody) -> List<T>?
    ) : ConverterTo<MApiResult<List<T>>> {

        return object : ConverterTo<MApiResult<List<T>>> {

            override suspend fun convert(result: KtorfitResult): MApiResult<List<T>> {

                val body = when (result) {
                    is KtorfitResult.Failure -> throw IllegalStateException()
                    is KtorfitResult.Success -> result.response.body<MTDResponseBody>()
                }

                return when {
                    body.status.code != 200 -> MResult.Err(body.status)
                    !(body.newChangeset)    -> MResult.NotModified(body.changesetID)
                    else                    -> MResult.Ok(getFromBody(body)!!, body.changesetID)
                }
            }
        }
    }

    private inline fun <reified T : MTDModel> unwrapListConverter(
        conv : ConverterTo<out MApiResult<List<T>>>
    ) : ConverterTo<MApiResult<T>> {

        return object : dev.ap5.mtdapi.rest.converters.ConverterTo<MApiResult<T>> {

            override suspend fun convert(result: KtorfitResult): MApiResult<T> {
                return when (val prev = conv.convert(result)) {
                    is MResult.NotModified -> prev
                    is MResult.Err         -> prev
                    is MResult.Ok          -> MResult.Ok(prev.value.first())
                }
            }
        }
    }

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): ConverterTo<*>? {
        val type = typeData.typeInfo.type

        if (type != MResult::class) return null

        val typeArg = typeData.typeArgs.first()
        val isList = typeArg.typeInfo.type == List::class

        val resultType = if (isList) {
            typeArg.typeArgs.first().typeInfo.type
        } else {
            typeArg.typeInfo.type
        }

        val converter = when (resultType) {
            CalendarDate::class -> mkConverter<CalendarDate> { it.calendarDates }
            Departure::class    -> mkConverter<Departure>    { it.departures }
            Route::class        -> mkConverter<Route>        { it.routes }
            ShapePoint::class   -> mkConverter<ShapePoint>   { it.shapePoints }
            Stop::class         -> mkConverter<Stop>         { it.stops }
            StopTime::class     -> mkConverter<StopTime>     { it.stopTimes }
            Itinerary::class    -> mkConverter<Itinerary>    { it.itineraries }
            Trip::class         -> mkConverter<Trip>         { it.trips }
            Vehicle::class      -> mkConverter<Vehicle>      { it.vehicles }
            APIUsageDay::class  -> mkConverter<APIUsageDay>  { it.apiUsageDays }
            else -> throw IllegalStateException()
        }

        return if (isList) converter else unwrapListConverter(converter)
    }
}