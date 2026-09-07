import { VMSG } from './validationMessages'
import {
  isRequired,
  maxLength,
  isHalfWidthNumeric,
  isHalfWidthNumericHyphen,
  isHalfWidthAlphaNumeric,
  isPostalCode
} from './validators'

export function validateMem101(form) {
  const errors = {}

  // Name 
  if (!isRequired(form.name)) {
    errors.name = VMSG.required('Name')
  } else if (!maxLength(form.name, 20)) {
    errors.name = VMSG.digitsMax('Name', 20)
  }

  // Password
  if (!isRequired(form.password)) {
    errors.password = VMSG.required('Password')
  } else if (!maxLength(form.password, 8)) {
    errors.password = VMSG.digitsMax('Password', 8)
  } else if (!isHalfWidthAlphaNumeric(form.password)) {
    errors.password = VMSG.halfWidthAlphaNumeric('Password')
  }

  // Password confirmation
  if (!isRequired(form.passwordConfirm)) {
    errors.passwordConfirm = VMSG.required('Password (Confirmation)')
  } else if (form.password !== form.passwordConfirm) {
    errors.passwordConfirm = VMSG.passwordMatch('Password', 'Password (Confirmation)')
  }

  // Age 
  if (!isRequired(form.age)) {
    errors.age = VMSG.required('Age')
  } else if (!isHalfWidthNumeric(form.age)) {
    errors.age = VMSG.halfWidthNumeric('Age')
  }

  // Gender 
  if (!isRequired(form.gender)) {
    errors.gender = VMSG.required('Gender')
  }

  // Zip Code 
  if (!isRequired(form.zip)) {
    errors.zip = VMSG.required('Zip Code')
  } else if (!isPostalCode(form.zip)) {
    errors.zip = VMSG.postalCode('Zip Code')
  }

  // Address 
  if (!isRequired(form.address)) {
    errors.address = VMSG.required('Address')
  } else if (!maxLength(form.address, 50)) {
    errors.address = VMSG.digitsMax('Address', 50)
  }

  // Phone Number 
  if (!isRequired(form.tel)) {
    errors.tel = VMSG.required('Phone Number')
  } else if (!isHalfWidthNumericHyphen(form.tel)) {
    errors.tel = VMSG.halfWidthNumericHyphen('Phone Number')
  } else if (!maxLength(form.tel, 20)) {
    errors.tel = VMSG.digitsMax('Phone Number', 20)
  }

  return errors
}