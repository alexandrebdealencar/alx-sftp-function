package 'com.alx.sftp';

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.management.AzureEnvironment;

public class AzureClientHelper {
    private static AzureResourceManager azureResourceManager;

    public static AzureResourceManager getAzureResourceManager() {
        if (azureResourceManager != null) {
            return azureResourceManager;
        }

        TokenCredential credential = new DefaultAzureCredentialBuilder().build();
        AzureProfile profile = new AzureProfile(
                System.getenv("AZURE_TENANT_ID"),
                System.getenv("AZURE_SUBSCRIPTION_ID"),
                AzureEnvironment.AZURE);

        return AzureResourceManager.authenticate(credential, profile)
                .withSubscription(System.getenv("AZURE_SUBSCRIPTION_ID"));
    }

    // Add a setter to allow injecting the mock
    public static void setAzureResourceManager(AzureResourceManager mockAzureResourceManager) {
        azureResourceManager = mockAzureResourceManager;
    }
}