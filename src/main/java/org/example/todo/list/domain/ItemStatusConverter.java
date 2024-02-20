package org.example.todo.list.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ItemStatusConverter implements AttributeConverter<ItemStatus, String> {

    @Override
    public String convertToDatabaseColumn(ItemStatus itemStatus) {
        if(StringUtils.isBlank(itemStatus.getStatus())){
            return null;
        }
        return itemStatus.getStatus();
    }

    @Override
    public ItemStatus convertToEntityAttribute(String itemStatus) {
        if(StringUtils.isBlank(itemStatus)){
            return null;
        }

        return Stream.of(ItemStatus.values())
                .filter(is -> is.getStatus().equalsIgnoreCase(itemStatus))
                .findFirst().orElseThrow(IllegalAccessError::new);
    }
}
