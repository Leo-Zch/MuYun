package net.ximatai.muyun.ability.curd.std;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import net.ximatai.muyun.ability.IDatabaseAbilityStd;
import net.ximatai.muyun.ability.IMetadataAbility;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public interface IUpdateAbility extends IDatabaseAbilityStd, IMetadataAbility {

    @POST
    @Path("/update/{id}")
    @Transactional
    default Integer update(@PathParam("id") String id, Map body) {
        HashMap map = new HashMap(body);
        map.put("id", id);
        if (!map.containsKey("t_update")) {
            map.put("t_update", LocalDateTime.now());
        }

        return getDatabase().updateItem(getSchemaName(), getMainTable(), map);
    }

}
