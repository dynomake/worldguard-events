# WorldGuard Events
## For server admins

This plugin may be required by some other plugins. If so, simply drop the JAR in the **plugins** folder of your server, reload it, and you're set.

  It doesn't do anything on its own and is useless unless required by another plugin.

## For developers
### Events
This API provides you with the following events :

   - **RegionsChangedEvent**
   - **RegionsEnteredEvent**
   - **RegionsLeftEvent**
   - **RegionEnteredEvent**
   - **RegionLeftEvent**

All of those are fired upon each player movement into a new set of regions, in that order.

They are all pretty self-explainatory. You can use any event interchangeably : the events returning a single ProtectedRegion are fired at the same time as the events returning a Set of ProtectedRegions (which is useful if you have multiple overlapping/neighbouring regions).

Cancelling any of the events will prevent the movement from happening.

### API
On top of events, and since version 1.15.2 of this plugin, we now provide you with a small API to get the most basic informations out of WorldGuard :

   - **getRegions**
   - **getRegionNames**
   - **isPlayerInRegion**
   - **isPlayerInAnyRegion**
   - **isPlayerInAllRegions**

All of those use the UUID of the player to fetch information about him from WorldGuard.

***Be aware that those region names are case insensitive, just like WorldGuard, and that they are checked for the world the player is currently in.***
## Usage example
later..

## [Spigot page](https://www.spigotmc.org/resources/worldguard-events.65176/)

### If you like this plugin, don't forget to rate it, to help other people discover it !
