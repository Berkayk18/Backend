# Configuration for `nix-shell` to setup a development environment.
# See https://nixos.org

let
  pkgs = (import <nixpkgs>) {};
  inherit (pkgs) mkShell postgresql_14;
  appDir = builtins.toString ./.;
in
  mkShell {
    name = "Canvas dev shell";

    venvDir = "${appDir}/.venv";

    buildInputs = with pkgs; [
      git
      gnumake
      flock
      python312
      python312.pkgs.wheel
      python312.pkgs.venvShellHook
      curl
    ];

    postShellHook = ''
      USER_POLICY_PATH="$HOME/.config/containers"
      USER_POLICY_FILE="$USER_POLICY_PATH/policy.json"
      GLOBAL_POLICY_FILE="/etc/containers/policy.json"
      if [[ ! -e $USER_POLICY_FILE && ! -e $GLOBAL_POLICY_FILE ]]; then
        echo "Create empty $USER_POLICY_FILE"
        mkdir -p $USER_POLICY_PATH
        echo '{"default": [{"type": "insecureAcceptAnything"}]}' | jq > $USER_POLICY_FILE
      fi

      current_git_branch() {
        git branch --show-current 2> /dev/null | sed -e 's/\(.*\)/(\1)/'
      }
      export PS1="[Canvas] \[\e[32m\]\w \[\e[91m\]\$(current_git_branch)\[\e[00m\]$ "

      cd ${appDir}

      echo "Installing requirements"
      unset SOURCE_DATE_EPOCH
      python3 -m pip install --disable-pip-version-check -q -r ${appDir}/requirements/requirements.txt
      python3 -m pip install --disable-pip-version-check -q -r ${appDir}/requirements/requirements-dev.txt
      pre-commit install --install-hooks

      if docker ps >/dev/null 2>&1; then
          echo "Docker is available, good!"
          echo ""
          make help
      else
          echo ""
          echo "Docker seems to be missing!"
          echo "Install Docker or Docker desktop so the docker command can be used"
          echo ""
      fi
    '';
  }
