package doctorhoai.learn.aiservice.model.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

import org.postgresql.util.PGobject;

import java.sql.SQLException;

@Converter(autoApply = true)
public class FloatArrayVectorConverter implements AttributeConverter<float[], Object> {

    @Override
    public Object convertToDatabaseColumn(float[] attribute) {
        if (attribute == null) return null;

        PGobject pgObject = new PGobject();
        pgObject.setType("vector");

        String vectorString = Arrays.toString(attribute)
                .replace("[", "[")
                .replace("]", "]");

        try {
            pgObject.setValue(vectorString);
        } catch (SQLException e) {
            throw new RuntimeException("Error setting PGobject value", e);
        }

        return pgObject;
    }

    @Override
    public float[] convertToEntityAttribute(Object dbData) {
        if (dbData == null) return new float[0];
        String text = dbData.toString();
        String[] parts = text.replace("[", "").replace("]", "").split(",");
        float[] result = new float[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Float.parseFloat(parts[i].trim());
        }
        return result;
    }
}



