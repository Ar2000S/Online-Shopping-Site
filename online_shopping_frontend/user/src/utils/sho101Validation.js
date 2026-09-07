import { VMSG } from './validationMessages'
import { isHalfWidthNumeric } from './validators'

// SHO101 Product Search and price range validation

export function validateSho101(filters) {
  const errors = {}

  // Price Lower 
  if (filters.priceLower !== '' && filters.priceLower != null) {
    if (!isHalfWidthNumeric(filters.priceLower)) {
      errors.priceLower = VMSG.halfWidthNumeric('Price (Lower Limit)')
    } else if (Number(filters.priceLower) <= 0) {
      errors.priceLower = VMSG.positive('Price (Lower Limit)')
    }
  }

  // Price Upper 
  if (filters.priceUpper !== '' && filters.priceUpper != null) {
    if (!isHalfWidthNumeric(filters.priceUpper)) {
      errors.priceUpper = VMSG.halfWidthNumeric('Price (Upper Limit)')
    } else if (Number(filters.priceUpper) <= 0) {
      errors.priceUpper = VMSG.positive('Price (Upper Limit)')
    }
  }

  // Consistency 
  if (!errors.priceLower && !errors.priceUpper &&
      filters.priceLower !== '' && filters.priceUpper !== '' &&
      filters.priceLower != null && filters.priceUpper != null) {
    if (Number(filters.priceUpper) < Number(filters.priceLower)) {
      errors.priceUpper = VMSG.upperLower('Price (Upper Limit)', 'Price (Lower Limit)')
    }
  }

  return errors
}