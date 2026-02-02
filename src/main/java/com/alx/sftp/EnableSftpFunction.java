package 'com.alx.sftp';

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import com.azure.resourcemanager.AzureResourceManager;
import java.util.logging.Logger;

public class EnableSftpFunction {
    @FunctionName("EnableSftp")
    public void enableSftp(
        @TimerTrigger(name = "EnableSftpTrigger", schedule = "%ENABLE_SFTP_SCHEDULE%") String timerInfo,
        final ExecutionContext context) {

        Logger logger = context.getLogger();
        logger.info("⏳ Triggered Enable SFTP function.");

        String resourceGroupName = System.getenv("RESOURCE_GROUP_NAME");
        String storageAccountName = System.getenv("STORAGE_ACCOUNT_NAME");

        try {
            AzureResourceManager azure = AzureClientHelper.getAzureResourceManager();

            azure.storageAccounts().manager().serviceClient()
                    .getStorageAccounts()
                    .update(resourceGroupName, storageAccountName,
                            new com.azure.resourcemanager.storage.models.StorageAccountUpdateParameters()
                                    .withIsSftpEnabled(true));

            logger.info("✅ SFTP has been enabled for storage account: " + storageAccountName);
        } catch (Exception e) {
            logger.severe("❌ Failed to enable SFTP: " + e.getMessage());
        }
    }
}