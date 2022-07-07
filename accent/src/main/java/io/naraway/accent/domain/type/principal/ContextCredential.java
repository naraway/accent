/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type.principal;

import io.naraway.accent.domain.type.IdName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContextCredential implements Serializable {
    //
    // NOTE: workspace types to office types
    private String loginId;
    private String displayName;
    @SuppressWarnings("java:S1948")
    private List<IdName> workspaces;
    private String station;
    private List<String> metros;
    private List<String> towns;
}
