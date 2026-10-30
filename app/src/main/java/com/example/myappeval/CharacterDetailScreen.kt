package com.example.myappeval

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myappeval.model.Character
import com.example.myappeval.model.CharacterUiState

// Remplace les valeurs null, vides ou "unknown" par "Inconnu"
private fun String?.orUnknown(): String =
    if (this.isNullOrBlank() || this.equals("unknown", ignoreCase = true)) "Inconnu" else this

@Composable
fun CharacterDetailScreen(
    state: CharacterUiState<Character>,
    onRetry: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Retour"
            )
        }

        when (state) {
            is CharacterUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CharacterUiState.Success -> {
                DetailContent(character = state.data)
            }

            is CharacterUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.message)
                    Button(
                        onClick = onRetry,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Réessayer")
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailContent(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = character.img,
            contentDescription = character.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(
            text = character.name.orUnknown(),
            style = MaterialTheme.typography.headlineMedium
        )

        DetailRow("Alias", character.alias?.joinToString(", ").orUnknown())
        DetailRow("Espèce", character.species?.joinToString(", ").orUnknown())
        DetailRow("Genre", character.gender.orUnknown())
        DetailRow("Âge", character.age?.content.orUnknown())
        DetailRow("Lieu de naissance", character.birthplace.orUnknown())
        DetailRow("Résidence", character.residence.orUnknown())
        DetailRow("Statut", character.status.orUnknown())
        DetailRow("Occupation", character.occupation.orUnknown())
        DetailRow("Régiment", character.groups?.firstOrNull()?.name.orUnknown())
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}