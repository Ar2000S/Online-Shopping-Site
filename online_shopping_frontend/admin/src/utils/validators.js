// "Half-width" (hankaku) character checks — common across many screens per the spec's style

export function isHalfWidthAlphaNumeric(value) {
  return /^[A-Za-z0-9]*$/.test(value)
}

export function isHalfWidthNumeric(value) {
  return /^[0-9]*$/.test(value)
}

export function isHalfWidthNumericHyphen(value) {
  return /^[0-9-]*$/.test(value)
}

export function isPostalCode(value) {
  // Japanese postal code: 3 digits, hyphen, 4 digits — fits ZIP varchar(8) exactly
  return /^[0-9]{3}-[0-9]{4}$/.test(value)
}

export function isRequired(value) {
  return value !== null && value !== undefined && String(value).trim() !== ''
}

export function maxLength(value, max) {
  return String(value ?? '').length <= max
}
