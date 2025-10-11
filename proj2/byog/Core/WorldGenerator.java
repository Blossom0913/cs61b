package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generates procedural dungeon-style worlds composed of rectangular rooms and
 * L-shaped hallways. All randomness is driven by the provided seed so that
 * identical seeds yield identical layouts.
 */
public class WorldGenerator {
    private static final int MIN_ROOM_SIZE = 3;
    private static final int MAX_ROOM_SIZE = 9;
    private static final int MIN_ROOM_COUNT = 12;
    private static final int MAX_ROOM_COUNT = 22;

    private final int width;
    private final int height;
    private final Random random;
    private final TETile[][] world;
    private final List<Room> rooms;

    public WorldGenerator(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.random = new Random(seed);
        this.world = new TETile[width][height];
        this.rooms = new ArrayList<>();
        fillWithNothing();
    }

    public TETile[][] generate() {
        generateRooms();
        connectRoomsWithHallways();
        addWalls();
        return world;
    }

    private void fillWithNothing() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private void generateRooms() {
        int targetRoomCount = RandomUtils.uniform(random, MIN_ROOM_COUNT, MAX_ROOM_COUNT + 1);
        int attempts = 0;
        int maxAttempts = targetRoomCount * 8;

        while (rooms.size() < targetRoomCount && attempts < maxAttempts) {
            attempts += 1;
            int roomWidth = RandomUtils.uniform(random, MIN_ROOM_SIZE, MAX_ROOM_SIZE + 1);
            int roomHeight = RandomUtils.uniform(random, MIN_ROOM_SIZE, MAX_ROOM_SIZE + 1);

            if (roomWidth >= width - 2 || roomHeight >= height - 2) {
                continue; // defensive guard for tiny worlds
            }

            int x = RandomUtils.uniform(random, 1, width - roomWidth - 1);
            int y = RandomUtils.uniform(random, 1, height - roomHeight - 1);

            Room candidate = new Room(x, y, roomWidth, roomHeight);
            if (canPlace(candidate)) {
                rooms.add(candidate);
                carveRoom(candidate);
            }
        }
    }

    private boolean canPlace(Room candidate) {
        for (Room room : rooms) {
            if (room.intersectsWithPadding(candidate)) {
                return false;
            }
        }
        return true;
    }

    private void carveRoom(Room room) {
        for (int x = room.x; x < room.x + room.width; x += 1) {
            for (int y = room.y; y < room.y + room.height; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    private void connectRoomsWithHallways() {
        if (rooms.size() < 2) {
            return;
        }

        List<Room> shuffledRooms = new ArrayList<>(rooms);
        Collections.shuffle(shuffledRooms, random);

        Room previous = shuffledRooms.get(0);
        for (int i = 1; i < shuffledRooms.size(); i += 1) {
            Room current = shuffledRooms.get(i);
            carveHallway(previous.center(), current.center());
            previous = current;
        }

        int additionalConnections = Math.max(1, rooms.size() / 4);
        for (int i = 0; i < additionalConnections; i += 1) {
            Room a = shuffledRooms.get(RandomUtils.uniform(random, shuffledRooms.size()));
            Room b = shuffledRooms.get(RandomUtils.uniform(random, shuffledRooms.size()));
            if (a == b) {
                i -= 1;
                continue;
            }
            carveHallway(a.center(), b.center());
        }
    }

    private void carveHallway(Point start, Point end) {
        boolean horizontalFirst = random.nextBoolean();

        if (horizontalFirst) {
            carveHorizontal(start.x, end.x, start.y);
            carveVertical(start.y, end.y, end.x);
        } else {
            carveVertical(start.y, end.y, start.x);
            carveHorizontal(start.x, end.x, end.y);
        }
    }

    private void carveHorizontal(int xStart, int xEnd, int y) {
        int min = Math.max(1, Math.min(xStart, xEnd));
        int max = Math.min(width - 2, Math.max(xStart, xEnd));
        for (int x = min; x <= max; x += 1) {
            world[x][y] = Tileset.FLOOR;
        }
    }

    private void carveVertical(int yStart, int yEnd, int x) {
        int min = Math.max(1, Math.min(yStart, yEnd));
        int max = Math.min(height - 2, Math.max(yStart, yEnd));
        for (int y = min; y <= max; y += 1) {
            world[x][y] = Tileset.FLOOR;
        }
    }

    private void addWalls() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (world[x][y] != Tileset.NOTHING) {
                    continue;
                }
                if (isAdjacentToFloor(x, y)) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    private boolean isAdjacentToFloor(int x, int y) {
        for (int dx = -1; dx <= 1; dx += 1) {
            for (int dy = -1; dy <= 1; dy += 1) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                int nx = x + dx;
                int ny = y + dy;
                if (withinBounds(nx, ny) && world[nx][ny] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean withinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private static final class Room {
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        private Room(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        private Point center() {
            int centerX = x + width / 2;
            int centerY = y + height / 2;
            return new Point(centerX, centerY);
        }

        private boolean intersectsWithPadding(Room other) {
            int thisLeft = this.x - 1;
            int thisRight = this.x + this.width;
            int thisBottom = this.y - 1;
            int thisTop = this.y + this.height;

            int otherLeft = other.x - 1;
            int otherRight = other.x + other.width;
            int otherBottom = other.y - 1;
            int otherTop = other.y + other.height;

            return !(otherRight < thisLeft
                    || otherLeft > thisRight
                    || otherTop < thisBottom
                    || otherBottom > thisTop);
        }
    }
}
