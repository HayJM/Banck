package br.com.dio.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MoneyAudit(UUID transactionId,
                         BanckService targetService,
                         String description,
                         OffsetDateTime createdAt){

    // If you need custom logic, use a static factory method instead:
    public static MoneyAudit of(UUID randomUUID, BanckService service, String description2, OffsetDateTime now) {
        return new MoneyAudit(randomUUID, service, description2, now);
    }

}
