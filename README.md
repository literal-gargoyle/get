# get

PasteItemPlugin is a simple Bukkit/Spigot plugin that allows players to paste detailed information about the item they are holding in their main hand to [Hastebin](https://paste.md-5.net/) using the `/get` command.

## Features
- Retrieves and formats item information including:
  - Item type
  - Amount
  - Custom name (if any)
  - Lore (if any)
  - Item flags
- Uploads the item information to Hastebin.
- Provides a shareable link to the player.

## Installation
1. Download the compiled `.jar` file.
2. Place the `.jar` file in your server's `plugins` directory.
3. Restart or reload your server.

## Usage
1. Hold the item you want to analyze in your main hand.
2. Run the command `/get`.
3. If successful, you will receive a link to the Hastebin paste containing your item's information.

### Example Output
If you are holding a custom diamond sword with a custom name and lore:

```
Item Type: DIAMOND_SWORD
Amount: 1
Display Name: Excalibur
Lore: The legendary sword, Sharpness V
Item Flags: [HIDE_ATTRIBUTES]
```

The plugin will upload this information and return a link like: `https://paste.md-5.net/abc123`

## Commands
### /get
- **Description**: Retrieves the information of the item in the player's main hand and uploads it to Hastebin.
- **Permission**: No special permissions required.

## Configuration
No configuration is required for this plugin.

## Requirements
- Java 8 or newer.
- Bukkit/Spigot server.

## Troubleshooting
- **"You are not holding an item"**: Ensure you are holding an item in your main hand when running the command.
- **Failed to paste item information**: Check your server's console for error messages related to network issues or API connectivity.

## License
This plugin is provided as open-source and is free to use and modify.

## Contributing
Contributions are welcome! Feel free to fork the project and submit pull requests.

