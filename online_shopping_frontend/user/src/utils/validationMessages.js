// Centralized validation message templates

export const VMSG = {
  required: (field) => `${field} must be entered.`,
  halfWidthNumeric: (field) => `${field} must be entered as half-width numeric.`,
  halfWidthNumericHyphen: (field) => `${field} must be entered using half-width numbers and hyphens.`,
  halfWidthAlphaNumeric: (field) => `${field} must be entered using half-width alphanumeric characters.`,
  validDate: (field) => `${field} must contain a valid date.`,
  positive: (field) => `${field} must be entered as a positive number.`,
  digitsExact: (field, n) => `${field} must be entered with ${n} digits.`,
  digitsMax: (field, n) => `${field} must be entered with ${n} digits or fewer.`,
  postalCode: (field) => `${field} must be entered in the format XXX-XXXX.`,
  passwordMatch: (f1, f2) => `${f1} and ${f2} must have the same value.`,
  dateOrder: (start, end) => `${end} must be entered as a date after ${start}.`,
  upperLower: (upper, lower) => `${upper} must be greater than ${lower}.`
}

export const SMSG = {
  MSG006: 'Please select a product.',
  MSG007: 'Please enter a purchase quantity between 1 and 999.',
  MSG008: 'Insufficient stock. Please adjust your purchase quantity.',
  MSG009: 'Please select the item(s) to cancel.'
}