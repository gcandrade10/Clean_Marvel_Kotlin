package com.puzzlebench.cmk.data.mapper.service

import com.puzzlebench.cmk.data.service.response.CharacterResponse
import com.puzzlebench.cmk.data.service.response.ThumbnailResponse
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.model.Thumbnail


class CharacterMapperService : BaseMapperService<CharacterResponse, Character> {

    override fun transform(characterResponse: CharacterResponse): Character = Character(
            id = characterResponse.id,
            name = characterResponse.name,
            description = characterResponse.description,
            thumbnail = transformToThumbnail(characterResponse.thumbnail)
    )

    override fun transformToResponse(type: Character): CharacterResponse = CharacterResponse(
            id = type.id,
            name = type.name,
            description = type.description,
            thumbnail = transformToThumbnailResponse(type.thumbnail)
    )

    fun transformToThumbnail(thumbnailResponse: ThumbnailResponse): Thumbnail = Thumbnail(
            thumbnailResponse.path,
            thumbnailResponse.extension
    )

    fun transformToThumbnailResponse(thumbnail: Thumbnail): ThumbnailResponse = ThumbnailResponse(
            thumbnail.path,
            thumbnail.extension
    )

    fun transform(charactersResponse: List<CharacterResponse>): List<Character> = charactersResponse.map { transform(it) }

}