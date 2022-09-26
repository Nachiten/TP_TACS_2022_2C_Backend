package com.tacs.backend.model;

public enum ErrorCode {
  // --- Match ---
  MATCH_EXISTENT,
  MATCH_NOT_FOUND,
  MATCH_FULL,
  INVALID_MATCH_DATE,

  // --- User error ---
  INVALID_QUERY_PARAM,
  MISSING_QUERY_PARAM,
  INVALID_BODY,

  // --- Player ---
  PLAYER_EXISTENT;
}
