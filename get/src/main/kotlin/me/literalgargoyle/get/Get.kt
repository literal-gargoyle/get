package me.literalgargoyle.get

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class get : JavaPlugin(), CommandExecutor {
    override fun onEnable() {
        getCommand("get")!!.setExecutor(this)
        logger.info("PasteItemPlugin enabled.")
    }

    override fun onDisable() {
        logger.info("PasteItemPlugin disabled.")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(ChatColor.RED.toString() + "Only players can run this command.")
            return true
        }

        val player = sender
        val item = player.inventory.itemInMainHand

        if (item == null || item.type == Material.AIR) {
            player.sendMessage(ChatColor.RED.toString() + "You are not holding an item.")
            return true
        }

        val itemData = getItemInformation(item)
        val pasteUrl = sendToHastebin(itemData)

        if (pasteUrl != null) {
            player.sendMessage(ChatColor.GREEN.toString() + "Item information pasted: " + pasteUrl)
        } else {
            player.sendMessage(ChatColor.RED.toString() + "Failed to paste item information.")
        }

        return true
    }

    private fun getItemInformation(item: ItemStack): String {
        val builder = StringBuilder()
        builder.append("Item Type: ").append(item.type.toString()).append("\n")
        builder.append("Amount: ").append(item.amount).append("\n")

        if (item.hasItemMeta() && item.itemMeta != null) {
            if (item.itemMeta!!.hasDisplayName()) {
                builder.append("Display Name: ").append(item.itemMeta!!.displayName).append("\n")
            }

            if (item.itemMeta!!.hasLore()) {
                builder.append("Lore: ").append(java.lang.String.join(", ", item.itemMeta!!.lore)).append("\n")
            }

            builder.append("Item Flags: ").append(item.itemMeta!!.itemFlags.toString()).append("\n")
        }

        return builder.toString()
    }

    private fun sendToHastebin(content: String): String? {
        try {
            val url = URL("https://paste.md-5.net/documents")
            val connection = url.openConnection() as HttpURLConnection
            connection.doOutput = true
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8")

            connection.outputStream.use { outputStream ->
                outputStream.write(content.toByteArray(StandardCharsets.UTF_8))
            }
            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
                val key =
                    response.split("\"key\":\"".toRegex(), limit = 2).toTypedArray()[1].split("\"".toRegex(), limit = 2)
                        .toTypedArray()[0]
                return "https://paste.md-5.net/$key"
            }
        } catch (e: Exception) {
            logger.severe("Error sending data to Hastebin: " + e.message)
        }

        return null
    }
}