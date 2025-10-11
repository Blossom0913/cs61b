package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Lightweight sanity checks for the {@link WorldGenerator}. Runs without any
 * external testing framework so we can verify deterministic behavior quickly.
 */
public final class WorldGeneratorTest {
    private WorldGeneratorTest() {
    }

    public static void main(String[] args) {
        long seed = 123456789L;
        WorldGenerator generator = new WorldGenerator(Game.WIDTH, Game.HEIGHT, seed);
        TETile[][] world = generator.generate();

        require(hasTile(world, Tileset.FLOOR), "Generated world must contain floor tiles");
        require(hasTile(world, Tileset.WALL), "Generated world must contain wall tiles");
        require(hasMoreThanOneRoom(world), "Generated world should have multiple floor regions");

        WorldGenerator generator2 = new WorldGenerator(Game.WIDTH, Game.HEIGHT, seed);
        TETile[][] world2 = generator2.generate();
        String worldAsString = TETile.toString(world);
        String world2AsString = TETile.toString(world2);
        require(worldAsString.equals(world2AsString),
                "World generation must be deterministic for identical seeds");

        System.out.println("WorldGenerator sanity checks passed.");
    }

    private static boolean hasTile(TETile[][] world, TETile target) {
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                if (world[x][y] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasMoreThanOneRoom(TETile[][] world) {
        int transitions = 0;
        for (int x = 0; x < world.length; x += 1) {
            boolean inFloorColumn = false;
            for (int y = 0; y < world[0].length; y += 1) {
                if (world[x][y] == Tileset.FLOOR) {
                    if (!inFloorColumn) {
                        transitions += 1;
                    }
                    inFloorColumn = true;
                } else {
                    inFloorColumn = false;
                }
            }
        }
        return transitions > 1;
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
