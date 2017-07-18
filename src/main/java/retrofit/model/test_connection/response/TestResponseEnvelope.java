package retrofit.model.test_connection.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")

public class TestResponseEnvelope {
    @Element(name = "Header", required = false)
    String header;

    @Element(name = "return")
    @Path("Body/ConfirmConnectionsResponse")
    TestResponseData testResponseData;

    public TestResponseData getTestData() {
        return testResponseData;
    }
}