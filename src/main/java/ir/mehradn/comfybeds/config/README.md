# Configurations Guide
This mod uses [MidnightLib](https://github.com/TeamMidnightDust/MidnightLib) for configurations. The main file to edit for the configurations is located at `.minecraft/config/comfy-beds.json`. The mod provides a GUI config screen that you can use if you have a mod such as ModMenu or MidnightLib installed and enabled. I suggest you create your configurations using the GUI on a Minecraft client, and the copying the configuration file to your server.

### Allow Resting During The Day (`allowRestAtDay`)
If enabled, you are allowed to rest during the day. However, the day will not be skipped if it's before nighttime.

### Changing Respawn Point (`changeRespawn`)
- **Normal (`NORMAL`):** Same as vanilla, you will set your respawn point when you right-click a bed.
- **/setbed (`COMMAND`, Default):** You can use the `/setbed` command while you are resting in a bed to set your respawn point. Also, there will be a chat message that you can click on to run the command automatically. This option is ignored if "Allow Resting During The Day" is turned off.
- **Shift Click (`SHIFT_CLICK`):** You can set your respawn point by shift-clicking a bed. Normal right clicks will no longer set your respawn point.
- **Don't Shift Click (`NOT_SHIFT_CLICK`):** The opposite of the "Shift Click" option, you must shift-click to rest without setting your respawn point.

### Provide Instructions When Resting (`provideInstructions`)
If enabled, you will be provided with some instructions about setting your respawn point. <br>
If disabled, the chat message for setting your respawn point if the "/setbed" option is chosen, is not going to be sent anymore. So you have to use the `/setbed` command manually. <br>
**Note:** Running the `/setbed` command while being outside of a bed will provide you with the instructions, even if this option is disabled.

### Beds Outside Of Overworld (`outsideOverworld`)
- **Explode (`EXPLODE`):** Same as vanilla, beds will explode outside of Overworld.
- **Monsters Nearby (`MONSTERS`, Default):** You will receive the "You may not rest now; there are monsters nearby" prompt when you try to rest outside of Overworld, it doesn't matter if a monster is actually nearby.
- **Rest (`REST`):** You will be allowed to rest outside of Overworld, you cannot set your respawn point though. This option is ignored if "Allow Resting During The Day" is turned off.