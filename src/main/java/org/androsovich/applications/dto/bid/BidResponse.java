package org.androsovich.applications.dto.bid;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import static org.androsovich.applications.constants.Constants.MAX_SIZE_NAME_USER;
import static org.androsovich.applications.constants.Constants.MIN_SIZE_NAME_USER;

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
