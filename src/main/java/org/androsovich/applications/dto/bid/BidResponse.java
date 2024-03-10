package org.androsovich.applications.dto.bid;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BidResponse extends RepresentationModel<BidResponse> {
    private long id;
    private String name;
    private String text;
    private String status;
    private String phone;
    private String createdTime;
    private String firstName;
    private String lastName;
}
