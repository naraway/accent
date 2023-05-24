package io.naraway.accent.domain.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventMessage implements DomainMessage {
    //
    private String additionalInfo;      // For EventNotice, etc
}