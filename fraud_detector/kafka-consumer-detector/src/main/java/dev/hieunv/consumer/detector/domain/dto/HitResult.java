package dev.hieunv.consumer.detector.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HitResult {

    private boolean isHit = false;
}
