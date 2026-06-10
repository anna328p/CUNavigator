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
		abiVersions = [ "x86_64" "arm64-v8a" ];
		includeNDK = true;
		platformVersions = [ "36" "36.1" "latest" ];
		useGoogleAPIs = true;
		buildToolsVersions = [ "36.0.0" "36.1.0" "37.0.0" ];
	};

	sdk = composition.androidsdk;

	studio = androidStudioPackages.beta.withSdk sdk;

	jbr_jdk = jetbrains.jdk;

in
	stdenv.mkDerivation {
		pname = "cu-navigator-env";
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
