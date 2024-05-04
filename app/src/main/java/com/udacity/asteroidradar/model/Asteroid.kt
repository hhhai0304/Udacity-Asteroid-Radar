package com.udacity.asteroidradar.model

import android.os.Parcelable
import com.udacity.asteroidradar.database.entity.AsteroidEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable

fun Asteroid.toEntity(): AsteroidEntity = AsteroidEntity(
    id = id,
    codename = codename,
    closeApproachDate = closeApproachDate,
    absoluteMagnitude = absoluteMagnitude,
    estimatedDiameter = estimatedDiameter,
    relativeVelocity = relativeVelocity,
    distanceFromEarth = distanceFromEarth,
    isPotentiallyHazardous = isPotentiallyHazardous,
)

fun List<Asteroid>.toEntities(): List<AsteroidEntity> = map { it.toEntity() }