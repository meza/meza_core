# Supporters Core

Supporters Core is a shared mod component that handles loading, refreshing, and formatting supporter data for display in Minecraft mods.

This library is designed for reuse across multiple mods. It provides a consistent and configurable way to present supporter tiers and recently joined members in config screens, credits, or anywhere else you want to celebrate your community.

## Features

- Loads supporter data from a remote JSON endpoint
- Automatically refreshes every 5 minutes
- Provides rainbow and gold-highlighted name formatting
- Differentiates tiered supporters from recent joiners
- Exposes a simple, static API for consumption by other mods

## Usage

Add this as a dependency in your mod:

```kotlin
repositories {
    maven { url = uri("https://maven.meza.gg/releases") }
}

dependencies {
    modImplementation("gg.meza:supporters_core-fabric:1.0.0")
}
```

During runtime, access the loaded supporter data via `SupportersCore`:

```java
List<TierEntry> tiers = SupportersCore.getTiers();
String newSupporters = SupportersCore.getNewSupportersText();
```

For styled display, use the raw `Text` versions from the loader:

```java
Text text = SupportersCore.getLoader().getNewSupportersText();
```

## JSON Format

Supporter data must be served in the following format:

```json
{
  "tiers": [
    {
      "name": "Testers",
      "emoji": "🧪",
      "members": [
        { "name": "meza", "emoji": "🧪" }
      ]
    }
  ],
  "joined7Days": [
    { "name": "meza", "emoji": "🧪" }
  ]
}
```

This structure is consumed as-is. The mod assumes names are already filtered, capped, and ordered for display.

## License

MIT

## Credits

Thanks to all the supporters who make this project possible!

<!-- marker:patrons-start -->

<!-- marker:patrons-end -->
