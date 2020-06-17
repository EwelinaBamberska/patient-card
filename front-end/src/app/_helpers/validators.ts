import { FormControl } from '@angular/forms';

export function numberValidator(c: FormControl) {
  const input = c.value.replace(/\s+/g,"");

  if(!isFinite(input) || input === ''){
    return {
      numberInvalid: true
    }
  }

  return null;
}