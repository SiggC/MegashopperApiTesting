package dev.megashopper.common.dtos;

import dev.megashopper.common.entities.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemResponse {

    private String itemId;

    private ResourceMetadataPayload metadata;

    public ItemResponse(Item item) {
        this.itemId = item.getItemId();
        this.metadata = new ResourceMetadataPayload(item.getMetadata());
    }


}
