/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.json.vo;

import io.naraway.accent.domain.type.IdName;
import io.naraway.accent.util.json.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station implements JsonSerializable {
    //
    private String id;
    private String name;
    private List<String> settings;
    private IdName site;
}
