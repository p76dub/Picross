package model.technical;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.ui.ArrayListGrid;
import model.ui.Box;
import model.ui.Grid;
import model.util.DataExtractionException;
import model.util.MetaLevel;
import model.util.NoLevelFoundException;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Permet de charger des grilles au format JSON.
 * Les grilles doivent avoir le format suivant : <pre>
 *     {
 *         name: "nom de la grille",
 *         width: largeur,
 *         height: hauteur,
 *         mode: "color" ou "black"
 *         grid: "00000000000000000000000000000000000000000000000000000000000000..."
 *     }
 * </pre>
 * Dans l'attribut grille, les valeurs suivantes sont autorisées : <pre>
 *     - 0 pour une case blanche (vide)
 *     - 1 pour une case noire (pleine)
 *     - 2 pour une case rouge (mode color uniquement)
 *     - 3 pour une case vert (mode color uniquement)
 *     - 4 pour une case bleu (mode color uniquement)
 *     - 5 pour une case violet (mode color uniquement)
 *     - 6 pour une case marron (mode color uniquement)
 *     - 7 pour une case jaune (mode color uniquement)
 * </pre>
 * Tout numéro supérieur à 1 est considéré comme noir si le mode est "black".
 */
public class JSONGridLoader implements GridLoader {
    //ATTRIBUTS
    private final Map<MetaLevel, Path> gridsMap;
    private final Path gridDirectory;

    //CONSTRUCTEUR
    public JSONGridLoader(Path directory) throws NoLevelFoundException {
        if (directory == null) {
            throw new AssertionError("L'argument 'directory' ne peut pas être nul");
        }
        gridDirectory = directory.toAbsolutePath();
        gridsMap = new HashMap<>();
        searchForGrids();
    }

    //REQUETES
    @Override
    public List<MetaLevel> getMetaLevels() {
        return new ArrayList<>(gridsMap.keySet());
    }

    @Override
    public Grid<Box> getGrid(MetaLevel level) throws DataExtractionException {
        return extractGrid(gridsMap.get(level), level);
    }

    //OUTILS
    private void searchForGrids() throws NoLevelFoundException {
        DirectoryStream<Path> stream = null;
        try {
            stream = Files.newDirectoryStream(gridDirectory);
        } catch (IOException e) {
            throw new NoLevelFoundException(gridDirectory, e.getMessage());
        }
        for (Path file: stream) {
            try {
                MetaLevel meta = extractMetaData(file.toAbsolutePath());
                gridsMap.put(meta, file.toAbsolutePath());
            } catch (DataExtractionException e) {
                continue;
            }
        }
        try {
            stream.close();
        } catch (IOException e) {
            throw new NoLevelFoundException(gridDirectory, e.getMessage());
        }
    }

    /**
     * Extrait les metadonnées d'une grille à partir du fichier .json
     * @pre <pre>
     *     file != null
     *     Files.exists(file) && Files.isReadable(file)
     * </pre>
     * @post <pre>
     *     extractMetaData(file) != null
     * </pre>
     * @param file le chemin vers le fichie JSON
     * @return les métadonnées sous forme d'un MetaLevel
     * @throws model.util.DataExtractionException une opération relative à l'extraction des données s'est mal déroulée
     */
    private MetaLevel extractMetaData(Path file) throws DataExtractionException {
        assert file != null;
        assert Files.exists(file) && Files.isReadable(file);

        // La lecture du fichier sera bufferisée
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(file);
        } catch (IOException e) {
            throw new DataExtractionException(e.getMessage());
        }

        // Analyse du fichier et récupération sous forme d'arbre
        JsonElement root = new JsonParser().parse(reader);

        // Construction des métadonnées
        if (root.isJsonObject()) {
            JsonObject rootObject = root.getAsJsonObject();
            MetaLevel result = null;
            try {
                result = new MetaLevel(
                        rootObject.get("name").getAsString(),
                        rootObject.get("mode").getAsString(),
                        new Dimension(
                                rootObject.get("width").getAsInt(),
                                rootObject.get("height").getAsInt()
                        )
                );
            } catch (NullPointerException e) {
                try {
                    reader.close();
                } catch (IOException ex){
                    throw new DataExtractionException(e.getMessage());
                }
                throw new DataExtractionException(e.getMessage());
            }
            try {
                reader.close();
            } catch (IOException e) {
                throw new DataExtractionException(e.getMessage());
            }
            return result;
        }
        try {
            reader.close();
        } catch (IOException ex){
            throw new DataExtractionException("Malformed JSON file");
        }
        throw new DataExtractionException("Malformed JSON file");
    }

    /**
     * Permet d'extraire du fichier JSON la grille du niveau. Converti ensuite les données extraites en une grille.
     * @pre <pre>
     *     file != null
     *     meta != null
     *     Files.exists(file) && Files.isReadable(file)
     * </pre>
     * @post <pre>
     *     extractGrid(file, meta) != null
     *     extractGrid(file, meta).getSize().equals(meta.getSize())
     * </pre>
     * @param file le chemin vers le fichier JSON
     * @param meta les metadonnées du niveau
     * @return une grille
     * @throws DataExtractionException lorsque l'extraction échoue
     */
    private Grid<Box> extractGrid(Path file, MetaLevel meta) throws DataExtractionException {
        assert file != null;
        assert meta != null;
        assert Files.exists(file) && Files.isReadable(file);

        // La lecture du fichier sera bufferisée
        BufferedReader reader = null;
        try {
            Files.newBufferedReader(file);
        } catch (IOException e) {
            throw new DataExtractionException(e.getMessage());
        }

        // Analyse du fichier et récupération sous forme d'arbre
        JsonElement root = new JsonParser().parse(reader);

        // Construction des métadonnées
        if (root.isJsonObject()) {
            JsonObject rootObject = root.getAsJsonObject();
            String rawGrid = null;
            try {
                rawGrid = rootObject.get("grid").getAsString();
            } catch (NullPointerException e) {
                throw new DataExtractionException(e.getMessage());
            }
            int height = (int) meta.getSize().getHeight();
            int width = (int) meta.getSize().getWidth();
            Grid<Box> grid = new ArrayListGrid<>();

            if (rawGrid.length() == height * width) {
                for (int i = 0; i < height; ++i) {
                    for (int j = 0; j < width; ++j) {
                        grid.setObjectAt(j, i, createRightBox(rawGrid.charAt(i*j + j), meta.getMode()));
                    }
                }
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new DataExtractionException("Malformed JSON file");
        }
        throw new DataExtractionException("Malformed JSON file");
    }

    private Box createRightBox(char number, String mode) throws DataExtractionException {
        switch (number) {
            case 0:
                return null; //TODO: mettre la bonne box
            case 2:
                if (mode.equals(MetaLevel.COLOR_MODE)) {
                    return null; //TODO: mettre la bonne box
                }
            case 3:
                if (mode.equals(MetaLevel.COLOR_MODE)) {
                    return null; //TODO: mettre la bonne box
                }
            case 4:
                if (mode.equals(MetaLevel.COLOR_MODE)) {
                    return null; //TODO: mettre la bonne box
                }
            case 5:
                if (mode.equals(MetaLevel.COLOR_MODE)) {
                    return null; //TODO: mettre la bonne box
                }
            case 6:
                if (mode.equals(MetaLevel.COLOR_MODE)) {
                    return null; //TODO: mettre la bonne box
                }
            case 7:
                if (mode.equals(MetaLevel.COLOR_MODE)) {
                    return null; //TODO: mettre la bonne box
                }
            case 1:
                return null; //TODO: mettre la bonne box
            default:
                throw new DataExtractionException("Unknown number in grid attribute");
        }
    }
}
