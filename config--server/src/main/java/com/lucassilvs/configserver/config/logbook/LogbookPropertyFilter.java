package com.lucassilvs.configserver.config.logbook;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.ResponseFilter;
import org.zalando.logbook.core.ResponseFilters;

@Configuration
public class LogbookPropertyFilter {

    private final Logger logger = LoggerFactory.getLogger(LogbookPropertyFilter.class);

    private final ObjectMapper objectMapper;

    public LogbookPropertyFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean match(HttpResponse response)  {
        try {
            return response.getBodyAsString().contains("version");
        }catch (Exception e){
            return false;
        }

    }
    @Bean
    public ResponseFilter responseFilter() {
        return ResponseFilter.merge(
                ResponseFilters.defaultValue(),
                ResponseFilters.replaceBody(response -> {
                    if (match(response)) {
                        try {
                            String bodyAsString = response.getBodyAsString();
                            if (StringUtils.isNotEmpty(bodyAsString)) {
                                ObjectNode objectNode = objectMapper.readValue(bodyAsString, ObjectNode.class);
                                JsonNode sources = objectNode.get("propertySources");
                                for (JsonNode node: sources) {
                                    ((ObjectNode) node).put("source", "<Ocultado>");
                                }
                                return objectMapper.writeValueAsString(objectNode);
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    return null;
                })
        );
    }
}
