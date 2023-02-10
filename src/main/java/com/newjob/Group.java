package com.newjob;

import java.util.Set;

public record Group(Set<String> members) {
    public int size() { return members.size(); }
}
