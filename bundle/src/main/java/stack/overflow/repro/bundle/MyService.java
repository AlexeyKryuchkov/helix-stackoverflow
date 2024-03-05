package stack.overflow.repro.bundle;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.RestfulResource;
import com.bmc.arsys.rx.services.common.annotation.AccessControlledMethod;
import com.bmc.arsys.rx.services.common.annotation.RxDefinitionTransactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("stackoverflow")
public class MyService implements RestfulResource {
    private static final String QUERY_TYPE_RECORD_DATA = "com.bmc.arsys.rx.application.record.datapage" +
            ".RecordInstanceDataPageQuery";

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @RxDefinitionTransactional(readOnly = false)
    @AccessControlledMethod(authorization = AccessControlledMethod.AuthorizationLevel.ValidUser)
    public Response respond(@NotNull @Valid MyRequest myRequest, @Context SecurityContext securityContext) {

        int pageSize = -1;
        int startIndex = 0;
        String qualification = LengthyQualification.get();
        List<String> propertySelections = Collections.singletonList("1");
        String recordDefinition = "stack-overflow-repro.stack-overflow-repro:Fruits";
        Map<String, List<String>> dataPageParams = new HashMap<>();
        dataPageParams.put("queryExpression", Collections.singletonList(qualification));
        dataPageParams.put("recorddefinition", Collections.singletonList(recordDefinition));
        dataPageParams.put("propertySelection", propertySelections);
        dataPageParams.put("dataPageType", List.of(QUERY_TYPE_RECORD_DATA));
        dataPageParams.put("pageSize", List.of(Integer.toString(pageSize)));
        dataPageParams.put("startIndex", List.of(Integer.toString(startIndex)));

        DataPageQueryParameters dataPageQueryParameters = new DataPageQueryParameters(dataPageParams);
        DataPage res = ServiceLocator.getRecordService().getRecordInstancesByIdDataPage(dataPageQueryParameters);

        String recordsRetrieved = Integer.toString(res.getData().size());

        return Response.status(200).entity("retrieved " + recordsRetrieved + " records").build();
    }
}