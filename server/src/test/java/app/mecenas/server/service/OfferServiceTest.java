package app.mecenas.server.service;

import app.mecenas.server.domain.User;
import app.mecenas.server.repo.ContractOfferRepo;
import app.mecenas.server.repo.WorkRepo;
import app.mecenas.server.security.Auth;
import app.mecenas.server.security.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OfferServiceTest {

  @Test
  void nonPartnerForbidden() {
    ContractOfferRepo offers = Mockito.mock(ContractOfferRepo.class);
    WorkRepo works = Mockito.mock(WorkRepo.class);
    Auth auth = Mockito.mock(Auth.class);

    User me = new User();
    me.setId(1L);
    me.setEmail("a@b.com");
    me.setRoles(Set.of(Role.READER));

    when(auth.currentUser()).thenReturn(Optional.of(me));

    OfferService service = new OfferService(offers, works, auth);
    ResponseEntity<?> resp = service.create(new OfferService.OfferCreate(1L, Map.of(), null));

    assertEquals(403, resp.getStatusCode().value());
  }
}
