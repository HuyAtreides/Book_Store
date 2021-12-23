package com.huyphan.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huyphan.dtos.TokenDto;
import com.huyphan.models.Token;

/** Token mapper. */
@Component
public class TokenMapper implements ToDtoMapper<TokenDto, Token>, FromDtoMapper<TokenDto, Token> {

	@Autowired
	private ModelMapper modelMapper;

	/** {@inheritDoc} */
	@Override
	public TokenDto toDto(Token data) {
		return modelMapper.map(data, TokenDto.class);
	}

	/** {@inheritDoc} */
	@Override
	public Token fromDto(TokenDto data) {
		return modelMapper.map(data, Token.class);
	}

}
