package app.mecenas.server.mapper;

import app.mecenas.server.domain.Campaign;
import app.mecenas.server.dto.CampaignDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CampaignMapper {
  @Mapping(target="workId", source="work.id")
  @Mapping(target="deadlineAt", expression = "java(c.getDeadlineAt()==null?null:c.getDeadlineAt().toString())")
  CampaignDto toDto(Campaign c);
}
