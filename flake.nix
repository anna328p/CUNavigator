{
	description = "TODO";

	inputs = {
		nixpkgs.url = github:nixos/nixpkgs/nixos-unstable;
	};

	outputs = { self
		, nixpkgs
		, ...
	}@flakes: let
		inherit (nixpkgs.lib) genAttrs systems;

		# forEachSystem' : (Str -> Set Any) -> (Set Any -> Set Any) -> Set (Set Any)
		forEachSystem' = env: body:
			genAttrs systems.flakeExposed (system: body (env system));

		env = system: rec {
			inherit system;

			pkgs = import nixpkgs {
				inherit system;

				config = {
					allowUnfree = true;
					android_sdk.accept_license = true;
				};
			};

			pkgName = "TODO";

			pkg = pkgs.callPackage ./default.nix { inherit flakes; };
		};

		forEachSystem = forEachSystem' env;

	in {
		packages = forEachSystem (env: let
			inherit (env) pkgName pkg;
		in {
			${pkgName} = pkg;
			default = pkg;
		});

		devShells = forEachSystem (env: let
			inherit (env) pkgName pkgs pkg;

			shell = import ./shell.nix { inherit flakes pkgs pkg; };
		in {
			${pkgName} = shell;
			default = shell;
		});
	};
}
