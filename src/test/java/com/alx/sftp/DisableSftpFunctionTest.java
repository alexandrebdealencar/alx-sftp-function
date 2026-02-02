package com.alx.sftp;

import com.microsoft.azure.functions.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.*;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class DisableSftpFunctionTest {

    @Mock
    private ExecutionContext context;

    @Mock
    private Logger logger;

    @InjectMocks
    private DisableSftpFunction disableSftpFunction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun_MissingEnvironmentVariables() {
        // Mock missing environment variables
        mockEnvironmentVariables(null, null);

        // Mock Logger
        when(context.getLogger()).thenReturn(logger);

        // Run the function
        disableSftpFunction.run(null, context);

        // Verify missing environment variables logging
        verify(logger, times(1)).severe("Missing required environment variables.");
    }

    // Helper method to mock environment variables
    private void mockEnvironmentVariables(String resourceGroup, String storageAccountName) {
        if (resourceGroup != null) {
            System.setProperty("RESOURCE_GROUP", resourceGroup);
        }
        if (storageAccountName != null) {
            System.setProperty("STORAGE_ACCOUNT_NAME", storageAccountName);
        }
    }
}