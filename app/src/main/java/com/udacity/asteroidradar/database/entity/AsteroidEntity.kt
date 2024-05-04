package com.udacity.asteroidradar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.model.Asteroid

@Entity(tableName = "Asteroid")
data class AsteroidEntity(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun AsteroidEntity.toModel(): Asteroid = Asteroid(
    id = id,
    codename = codename,
    closeApproachDate = closeApproachDate,
    absoluteMagnitude = absoluteMagnitude,
    estimatedDiameter = estimatedDiameter,
    relativeVelocity = relativeVelocity,
    distanceFromEarth = distanceFromEarth,
    isPotentiallyHazardous = isPotentiallyHazardous,
)

fun List<AsteroidEntity>.toModels(): List<Asteroid> = map { it.toModel() }