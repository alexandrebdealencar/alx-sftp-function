package 'com.alx.sftp';

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import com.azure.resourcemanager.AzureResourceManager;
import java.util.logging.Logger;

public class DisableSftpFunction {
    @FunctionName("DisableSftp")
    public void disableSftp(
        @TimerTrigger(name = "DisableSftpTrigger", schedule = "%DISABLE_SFTP_SCHEDULE%") String timerInfo,
        final ExecutionContext context) {

        Logger logger = context.getLogger();
        logger.info("⏳ Triggered Disable SFTP function.");

        String resourceGroupName = System.getenv("RESOURCE_GROUP_NAME");
        String storageAccountName = System.getenv("STORAGE_ACCOUNT_NAME");

        try {
            AzureResourceManager azure = AzureClientHelper.getAzureResourceManager();

            azure.storageAccounts().manager().serviceClient()
                    .getStorageAccounts()
                    .update(resourceGroupName, storageAccountName,
                            new com.azure.resourcemanager.storage.models.StorageAccountUpdateParameters()
                                    .withIsSftpEnabled(false));

            logger.info("✅ SFTP has been disabled for storage account: " + storageAccountName);
        } catch (Exception e) {
            logger.severe("❌ Failed to disable SFTP: " + e.getMessage());
        }
    }
}