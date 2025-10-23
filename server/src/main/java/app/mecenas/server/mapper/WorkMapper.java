package app.mecenas.server.mapper;

import app.mecenas.server.domain.Work;
import app.mecenas.server.dto.WorkDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkMapper {
  @Mapping(target="authorId", source="author.id")
  WorkDto toDto(Work w);
}
