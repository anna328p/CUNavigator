{ flakes
, stdenv
, lib
, androidStudioPackages
, androidenv
, jetbrains
, qt5
, nil
}:

let
	composition = androidenv.composeAndroidPackages {
		includeEmulator = true;
		includeSystemImages = true;
		abiVersions = [ "x86_64" "armeabi-v7a" "arm64-v8a" ];
		includeNDK = true;
		platformVersions = [ "34" "35" ];
		useGoogleAPIs = true;
		buildToolsVersions = [ "34.0.0" "35.0.0" ];
	};

	sdk = composition.androidsdk;

	studio = androidStudioPackages.stable.withSdk sdk;

	jbr_jdk = jetbrains.jdk;

in
	stdenv.mkDerivation {
		pname = "TODO";
		version = "0";

		nativeBuildInputs = [
			studio
			nil
		];

		buildInputs = [
			sdk
			jbr_jdk
		];

		IDEA_JDK = jbr_jdk;
		QT_QPA_PLATFORM = "xcb";

		meta = {
			maintainers = [ lib.maintainers.anna328p ];
		};
	}
