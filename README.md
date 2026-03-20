## Getting Started

To set up the development environment for this project, you'll need to install SDKMan, a tool for managing parallel versions of multiple Software Development Kits. Run the following command in your terminal to install SDKMan:
```bash
curl -s "https://get.sdkman.io" | bash
```

After installation, restart your terminal or run `source "$HOME/.sdkman/bin/sdkman-init.sh"` to initialize SDKMan. Once installed, navigate to the project directory and execute `sdk env install` to automatically install and configure the required Java version specified in the `.sdkmanrc` file.

## Code Formatting

This project uses Spotless to enforce consistent code formatting across Java, Markdown, YAML, and JSON files. Before committing your changes, ensure your code is properly formatted by running:
```bash
./gradlew spotlessApply
```

To check if your code adheres to the formatting rules without making changes, use:
```bash
./gradlew spotlessCheck
```

It's recommended to run `spotlessApply` before each commit to maintain code quality and avoid formatting-related issues in CI/CD pipelines.