package bobs.rockets {

    package launch {
        class Booster2
    }

    package betternavigation {
        
        class StarMap

        class BetterNavigator {
            // No need to say bobs.rockets.betternavigation.StarMap
            val map = new StarMap
        }
        package Tests {
            // In package bobs.rockets.betternavigation.tests
            class NavigatorSuite
        }
    }

    class Ship {
        // No need to import,
        // even though bobs.rockets.navigation.Navigator is in Navigation.scala
        // and not in BetterNavigation.scala

        val nav = new navigation.Navigator
    }

    package Fleets {
        class Fleet {
            def addShip(): Ship = {
                // No need to say bobs.rockets.Ship
                new Ship
            }
        }
    }
}

package bobs.rockets.betterfleets {
    class BetterFleet {
        // Even though we are in bobs.rockets, we didn't literally nest
        // and so, we need to write bobs.rockets
        def addShip: bobs.rockets.Ship = { new bobs.rockets.Ship }
    }
}

package launch {
    class Booster3
}