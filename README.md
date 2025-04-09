# Random_Image_Map

A simple Java project that generates a map-like image based on the Wave Function Collapse (WFC) algorithm.

## Overview

This project implements a basic version of the Wave Function Collapse algorithm to generate interesting and seemingly coherent patterns that resemble maps or terrains. It works by iteratively collapsing possibilities for each cell in a grid based on the constraints imposed by its neighbors.

## Features

* **Basic WFC Implementation:** Demonstrates the core principles of the Wave Function Collapse algorithm.
* **Configurable Grid Size:** Allows you to specify the dimensions (width and height) of the generated map.
* **Simple Tile Set:** Uses a predefined set of simple "tiles" (represented by colors in this basic version) to build the map.
* **Random Seed:** Option to provide a seed for the random number generator, ensuring reproducible results.
* **Image Output:** Saves the generated map as a PNG image file.

## Getting Started

### Prerequisites

* **Java Development Kit (JDK):** Ensure you have a compatible JDK installed on your system.

### How to Run

1.  **Clone the repository (if applicable):**
    ```bash
    git clone [repository_url]
    cd Random_Image_Map
    ```

2.  **Compile the Java code:**
    ```bash
    javac src/*.java
    ```

3.  **Run the main application:**
    ```bash
    java src.Main [width] [height] [seed (optional)] [output_filename (optional)]
    ```

    * `[width]`: The desired width of the generated map (integer).
    * `[height]`: The desired height of the generated map (integer).
    * `[seed (optional)]`: An integer seed for the random number generator. If not provided, a random seed will be used.
    * `[output_filename (optional)]`: The name of the output PNG file (e.g., `my_map.png`). If not provided, a default name will be used.

    **Example:**
    ```bash
    java src.Main 50 30
    java src.Main 100 80 12345
    java src.Main 60 40 987 map_output.png
    ```
    ## Further Development

This is a basic implementation and can be extended in various ways, such as:

* **More Complex Tile Sets:** Introducing more visually distinct and rule-based tiles.
* **Connectivity Rules:** Defining specific rules for how different tiles can connect.
* **Input Samples:** Using existing images or patterns as input to define the tile set and connection rules.
* **Backtracking:** Implementing backtracking mechanisms to handle situations where the algorithm gets stuck.
* **GUI:** Creating a graphical user interface for easier configuration and visualization.

## License

MIT License

## Author

Tomer Schwartz
