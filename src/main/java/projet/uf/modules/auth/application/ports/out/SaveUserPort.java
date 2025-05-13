package projet.uf.modules.auth.application.ports.out;

import projet.uf.modules.user.domain.model.User;

public interface SaveUserPort {
     User save(User user);
}
