package ru.inversion.rtgs.payload.reponse

import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
class MessageResponse(val message: String? = null
)
