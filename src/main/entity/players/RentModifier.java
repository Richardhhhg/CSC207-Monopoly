package main.entity.players;

public interface RentModifier {

    /**
     * Adjusts the rent based on the base rent value.
     *
     * @param baseRent The base rent value to be adjusted.
     * @return The adjusted rent value.
     */
    float adjustRent(float baseRent);
}
