# 🏓 CodiPing

A lightweight Minecraft plugin that replaces `[ping]` in chat with your real-time, color-coded ping. Built for Paper/Spigot with Folia and EssentialsX support.

## Requirements

- Paper / Folia 1.21+
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (required)
- [EssentialsX](https://essentialsx.net/) (optional)
- [EssentialsXChat](https://essentialsx.net/) (optional)

## Examples

**Tab-list**

![Tab-list](https://cdn.discordapp.com/attachments/993357002996711486/1481594471526432768/ezgif.com-animated-gif-maker.gif?ex=69b3e1e0&is=69b29060&hm=44d5c7f8564c80fbf9913d61b912129408d6b121d6e37d82dd2f53f191338c74)

**Nametag**

![Nametag](https://cdn.discordapp.com/attachments/993357002996711486/1481362286026293319/Java_versions2026.03.12-02.06.49.02-ezgif.com-video-to-gif-converter.gif?ex=69b309a3&is=69b1b823&hm=54eb8ea9ba25ef5ea2e4a0269e25a3e7a0ec7d003a7e148866c3e394b752b22f)

## Usage

**Chat tag** — type `[ping]` anywhere in chat and it's replaced with your current ping styled in a color gradient.

**PlaceholderAPI** — use `%codiping_ping%` in any PAPI-compatible plugin.

## Commands

| Command | Description |
|---|---|
| `/ping` | Shows your own ping |
| `/ping <player>` | Shows another player's ping |
| `/ping list` | Lists all online players and their pings |
| `/ping version` | Shows the plugin version |

## Ping Colors

| Range | Color |
|---|---|
| ≤ 60ms | 🟢 Green |
| 61 – 100ms | 🟡 Yellow-Green |
| 101 – 150ms | 🟡 Yellow |
| 151 – 200ms | 🟠 Orange |
| > 200ms | 🔴 Red |

## Building

```bash
git clone https://github.com/Codi4K/CodiPing.git
cd CodiPing
./gradlew shadowJar
```

Output: `build/libs/CodiPing-x.x.x.jar`

## Author

Made by **CodiMC** — [BuiltByBit](#) · [GitHub](https://github.com/Codi4K/CodiPing)
