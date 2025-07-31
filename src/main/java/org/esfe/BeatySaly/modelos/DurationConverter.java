package org.esfe.BeatySaly.modelos;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;

/**
 * Convierte un objeto Duration a y desde un Long, representando la duración en segundos.
 */
@Converter(autoApply = false)
public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        if (attribute == null) {
            return null;
        }
        // Convierte Duration al total de segundos.
        return attribute.toSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        if (dbData == null) {
            return null;
        }
        // Convierte el total de segundos de la base de datos de vuelta a un objeto Duration.
        return Duration.ofSeconds(dbData);
    }
}
