package eu.kanade.presentation.more.settings.screen.data

import android.text.format.Formatter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import eu.kanade.tachiyomi.util.storage.DiskUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite
import tachiyomi.presentation.core.theme.header
import java.io.File

@Composable
fun StorageInfo(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val storages by produceState(emptyList<File>()) {
        value = withContext(Dispatchers.IO) { DiskUtil.getExternalStorages(context) }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(GSpace.s3),
    ) {
        storages.forEach {
            StorageInfo(it)
        }
    }
}

@Composable
private fun StorageInfo(
    file: File,
) {
    val context = LocalContext.current

    val space by produceState(0L to 0L, file) {
        value =
            withContext(Dispatchers.IO) {
                DiskUtil.getAvailableStorageSpace(file) to DiskUtil.getTotalStorageSpace(file)
            }
    }
    val (available, total) = space
    val availableText = remember(available) { Formatter.formatFileSize(context, available) }
    val totalText = remember(total) { Formatter.formatFileSize(context, total) }

    Column(
        verticalArrangement = Arrangement.spacedBy(GSpace.s2),
    ) {
        Text(
            text = file.absolutePath,
            style = MaterialTheme.typography.header,
        )

        LinearProgressIndicator(
            modifier = Modifier
                .clip(GRadius.control)
                .fillMaxWidth()
                .height(GSpace.s2),
            progress = { if (total > 0) 1 - (available / total.toFloat()) else 0f },
        )

        Text(
            text = stringResource(MR.strings.available_disk_space_info, availableText, totalText),
            color = Graphite.colors.textSecondary,
            style = Graphite.type.small,
        )
    }
}
