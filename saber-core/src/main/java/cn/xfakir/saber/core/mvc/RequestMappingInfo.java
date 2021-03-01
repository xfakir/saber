package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.web.enums.RequestMethod;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class RequestMappingInfo {
    private Set<String> patternSet = Collections.singleton("");

    private Set<RequestMethod> requestMethodSet = Collections.emptySet();


    public RequestMappingInfo(Set<String> patternSet, Set<RequestMethod> requestMethodSet) {
        this.patternSet = patternSet;
        this.requestMethodSet = requestMethodSet;
    }

    public Set<String> getPatternSet() {
        return patternSet;
    }

    public Set<RequestMethod> getRequestMethodSet() {
        return requestMethodSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestMappingInfo that = (RequestMappingInfo) o;
        return Objects.equals(patternSet, that.patternSet) &&
                Objects.equals(requestMethodSet, that.requestMethodSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patternSet, requestMethodSet);
    }
}
