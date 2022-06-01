package com.stc.leaves.employeeservice.document;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Leaves {
    private int totalAnnual;
    private int consumed;
    private int available;
}
